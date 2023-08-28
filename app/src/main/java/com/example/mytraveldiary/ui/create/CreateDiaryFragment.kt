package com.example.mytraveldiary.ui.create

import android.app.Activity.RESULT_OK
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mytraveldiary.R
import com.example.mytraveldiary.core.fragment.InjectionFragment
import com.example.mytraveldiary.data.db.Diary
import com.example.mytraveldiary.databinding.AddDiaryEntryBinding
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.android.material.textfield.TextInputLayout
import org.kodein.di.instance
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class CreateDiaryFragment : InjectionFragment(R.layout.add_diary_entry) {

    private val viewModel: DiaryViewModel by instance()
    lateinit var binding: AddDiaryEntryBinding
    private val calendar = Calendar.getInstance()
    private val imageUris = mutableListOf<Uri>()
    private var location = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = AddDiaryEntryBinding.inflate(inflater, container, false)

        // Initialize Places API with API Key
        Places.initialize(requireContext(), getApiKey())

        val autocompleteFragment =
            childFragmentManager.findFragmentById(R.id.autocomplete_fragment)
                    as AutocompleteSupportFragment

        autocompleteFragment.setPlaceFields(
            listOf(
                Place.Field.ID,
                Place.Field.NAME,
                Place.Field.LAT_LNG
            )
        )
        autocompleteFragment.setActivityMode(AutocompleteActivityMode.OVERLAY)
        autocompleteFragment.setHint("Search destination")

        // Handle place selection
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                // Handle selected location (place.name, place.latLng)
                location = place.name as String
            }

            override fun onError(status: Status) {
                // Handle errors
            }
        })

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val addImageButton = binding.addImageButton

        addImageButton.setOnClickListener {
            openImagePicker()
        }

        binding.addButton.setOnClickListener {
            //check if values are provided then validate
            addDiaryEntry()
        }

        binding.txtDate.setOnClickListener {
            showDatePickerDialog()
        }
        binding.apply {
            fieldWatcher(
                listOf(tilTitle, tilNotes, tilDate)
            )
        }
    }

    private fun addDiaryEntry() {
        binding.apply {
            val isValid = validateAllFields(listOf(tilTitle, tilNotes, tilDate))
            if (isValid) {
                val diaryEntry = Diary(
                    title = txtTitle.getNonNullable(),
                    date = txtDate.getNonNullable(),
                    location = location,
                    notes = txtNotes.getNonNullable(),
                    imagePaths = imageUris
                )
                viewModel.addDiaryEntry(diaryEntry)
                navigateToDiariesList()

            } else {
                Timber.d("All fields must be provided")
            }
        }
    }

    fun validateAllFields(fieldsToValidate: List<TextInputLayout>): Boolean {
        var isValid = true
        fieldsToValidate.forEach {
            if (it.editText!!.text.isEmpty()) {
                it.error = "This value is required."
                isValid = false
            }
        }
        return isValid
    }

    private fun fieldWatcher(layoutItems: List<TextInputLayout>) {
        for (item in layoutItems) {
            item.editText!!.doOnTextChanged { _, _, _, _ -> item.error = null }
        }
    }

    private fun openImagePicker() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && requestCode == GALLERY_REQUEST_CODE) {
            val selectedImageUri: Uri? = data?.data
            if (selectedImageUri != null) {
                imageUris.add(selectedImageUri)
                displayImages()
            }
        }
    }

    private fun displayImages() {
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context, 3)
            adapter = ImageAdapter(imageUris)
            setHasFixedSize(false)
        }
    }

    companion object {
        private const val GALLERY_REQUEST_CODE = 1
    }

    private fun showDatePickerDialog() {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year, monthOfYear, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, monthOfYear)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateEditText()
            },
            year,
            month,
            day
        )

        datePickerDialog.show()
    }

    private fun getApiKey(): String {
        // Initialize Places API with API Key
        val appContext = this.requireContext()
        val applicationInfo: ApplicationInfo = appContext.packageManager
            .getApplicationInfo(appContext.packageName, PackageManager.GET_META_DATA)
      return applicationInfo.metaData["GOOGLE_API_KEY"].toString()
    }
    private fun updateDateEditText() {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val selectedDate = dateFormat.format(calendar.time)
        binding.txtDate.setText(selectedDate)
    }

    private fun navigateToDiariesList() {
        val action = CreateDiaryFragmentDirections.actionAddDiaryToListDiaries()
        findNavController().navigate(action)
    }

    private fun EditText.getNonNullable(): String =
        if (text.isNullOrEmpty())
            ""
        else
            text!!.trim().toString()

}