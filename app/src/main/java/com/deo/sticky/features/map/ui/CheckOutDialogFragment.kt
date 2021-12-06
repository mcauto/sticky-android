package com.deo.sticky.features.map.ui

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.deo.sticky.R
import com.deo.sticky.features.checkin.CategoryViewModel
import com.deo.sticky.features.checkin.CheckInViewModel
import com.deo.sticky.features.checkin.PlaceViewModel

/**
 * 체크아웃 바텀시트 클릭 시 호출되는 다이얼로그
 */
class CheckOutDialogFragment : DialogFragment() {
    private val _checkInViewModel: CheckInViewModel by activityViewModels()
    private val _placeViewModel: PlaceViewModel by activityViewModels()
    private val _categoryViewModel: CategoryViewModel by activityViewModels()

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val inflater = requireActivity().layoutInflater
            val view = inflater.inflate(R.layout.dialog_checkout, null)
            view.findViewById<Button>(R.id.cancel).setOnClickListener {
                dialog?.cancel()
            }
            view.findViewById<Button>(R.id.ok).setOnClickListener {
                _checkInViewModel.timerStop()
                _checkInViewModel.onCheckOut()
                _placeViewModel.initialize()
                _categoryViewModel.initialize()

                dialog?.dismiss()
            }
            val builder = AlertDialog.Builder(it)
            builder.setView(view)
            val dialog = builder.create()
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
            dialog
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}
