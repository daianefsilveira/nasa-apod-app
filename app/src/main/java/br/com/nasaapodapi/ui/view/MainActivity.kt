package br.com.nasaapodapi.ui.view

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import br.com.nasaapodapi.ui.viewmodel.NasaViewModel
import br.com.nasaapodapi.data.model.NasaModel
import br.com.nasaapodapi.data.network.getRetrofitInstance
import br.com.nasaapodapi.data.repository.NasaRepository
import br.com.nasaapodapi.databinding.ActivityMainBinding
import com.bumptech.glide.Glide

class MainActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    private lateinit var binding: ActivityMainBinding

    private var mNasaApodData: NasaModel? = null

    private lateinit var nasaViewModel: NasaViewModel

    private lateinit var nasaRepository: NasaRepository

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//       nasaViewModel = getViewModel { NasaViewModel(nasaRepository) }
        nasaRepository = NasaRepository(getRetrofitInstance())
        nasaViewModel = NasaViewModel(nasaRepository)
        nasaViewModel.getNasaApodData(getTodayDate())
        binding.pbLoading.visibility = View.VISIBLE

        setupObserve()
        setupButton()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun setupObserve() {
        nasaViewModel.nasaApodData.observe(this, Observer {
            binding.pbLoading.visibility = View.GONE
            mNasaApodData = it
            Glide.with(this)
                .load(it.hdurl)
                .into(binding.ivApod);

            binding.tvTitle.text = it.title
            binding.tvDate.text = getFormattedDate(it.date.toString())
            binding.tvExplanation.text = it.explanation

        })
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun setupButton() {
        binding.btnSearch.setOnClickListener {
            val cal = Calendar.getInstance()

            val datePickerDialog = DatePickerDialog(
                this, this@MainActivity, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(
                    Calendar.DAY_OF_MONTH
                )
            )
            datePickerDialog.show()
        }
    }

    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.N)
    private fun getFormattedDate(date: String): String? {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd")
        val outputFormat = SimpleDateFormat("dd-MMM-yyyy")
        var dateFormat= SimpleDateFormat("dd-MMM-yyyy")
        val dates = inputFormat.parse(date);
        return outputFormat.format(dates)
    }

    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.N)
    fun getTodayDate(): String {
        val calendar = Calendar.getInstance()
        var dateFormat= SimpleDateFormat("yyyy-MM-dd")
        return dateFormat.format(calendar.time)

    }

    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val mCalendar = Calendar.getInstance()
        mCalendar[Calendar.YEAR] = year
        mCalendar[Calendar.MONTH] = month
        mCalendar[Calendar.DAY_OF_MONTH] = dayOfMonth

        var dateFormat= SimpleDateFormat("yyyy-MM-dd")

        nasaViewModel.getNasaApodData(dateFormat.format(mCalendar.time))
        binding.pbLoading.visibility = View.VISIBLE

    }
}


