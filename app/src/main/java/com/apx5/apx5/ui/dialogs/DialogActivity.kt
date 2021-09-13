package com.apx5.apx5.ui.dialogs

import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.apx5.apx5.R
import com.apx5.apx5.constants.PrDialogs
import com.apx5.apx5.datum.adapter.AdtTeamSelection
import com.apx5.apx5.ui.listener.PrSingleClickListener
import com.apx5.domain.param.HistoryDelParam

/**
 * DialogActivity
 */

class DialogActivity : AppCompatActivity() {

    companion object {
        /* Dialog 생성*/
        private fun prDialog(
            context: Context,
            layout: Int
        ): Dialog {

            val dialog = Dialog(context)
            dialog.apply {
                requestWindowFeature(Window.FEATURE_NO_TITLE)
                setContentView(layout)
                setCancelable(false)
            }

            return dialog
        }

        /* Layout Params 생성*/
        private fun prLayoutParams(
            dialog: Dialog
        ): WindowManager.LayoutParams {

            val lp = WindowManager.LayoutParams()
            lp.apply {
                copyFrom(dialog.window?.attributes)
                width = WindowManager.LayoutParams.WRAP_CONTENT
                height = WindowManager.LayoutParams.WRAP_CONTENT
            }

            return lp
        }

        /* Dialog - 서버사용불가*/
        fun dialogNoInternet(
            context: Context,
            func:() -> Unit
        ) {

            val dialog = prDialog(context, PrDialogs.NO_INTERNET.layout)
            val lp = prLayoutParams(dialog)

            dialog.apply {
                findViewById<View>(R.id.bt_close).setOnClickListener(object : PrSingleClickListener() {
                    override fun onSingleClick(view: View) { func() }
                })

                show()
                window?.attributes = lp
            }
        }

        /* Dialog - 일반에러*/
        fun dialogError(
            context: Context
        ) {

            val dialog = prDialog(context, PrDialogs.COMM_ERROR.layout)
            val lp = prLayoutParams(dialog)

            dialog.apply {
                findViewById<View>(R.id.bt_close).setOnClickListener(object : PrSingleClickListener() {
                    override fun onSingleClick(view: View) { dialog.dismiss() }
                })

                show()
                window?.attributes = lp
            }
        }

        /* Dialog - 팀선택*/
        fun dialogTeamSelect(
            context: Context,
            team: AdtTeamSelection,
            msg: String,
            func:(String) -> Unit
        ) {

            val dialog = prDialog(context, PrDialogs.TEAM_SELECT.layout)
            val lp = prLayoutParams(dialog)

            /* 안내문구*/
            val tvMsg = dialog.findViewById<TextView>(R.id.tv_content)
            tvMsg.text = msg

            /* 팀이미지*/
            val ivIcon = dialog.findViewById<ImageView>(R.id.iv_icon)
            ivIcon.setImageDrawable(ContextCompat.getDrawable(context, team.teamImage))

            /* 팀컬러*/
            val lytTeamInfo = dialog.findViewById<LinearLayout>(R.id.lyt_team_info)
            val lytAction = dialog.findViewById<LinearLayout>(R.id.lyt_action)
            lytTeamInfo.setBackgroundColor(team.teamColor)
            lytAction.setBackgroundColor(team.teamColor)

            /* 팀코드*/
            val teamCode = team.teamCode

            /* 취소버튼*/
            dialog.apply {
                findViewById<View>(R.id.bt_cancel).setOnClickListener(object : PrSingleClickListener() {
                    override fun onSingleClick(view: View) { dialog.dismiss() }
                })

                /* 진행버튼*/
                findViewById<View>(R.id.bt_close).setOnClickListener(object : PrSingleClickListener() {
                    override fun onSingleClick(view: View) {
                        func(teamCode)
                        dismiss()
                    }
                })

                show()
                window?.attributes = lp
            }
        }

        /* Dialog - 기록삭제*/
        fun dialogHistoryDelete(
            context: Context,
            delHistory: HistoryDelParam,
            func:(HistoryDelParam) -> Unit
        ) {

            val dialog = prDialog(context, PrDialogs.DELETE_HISTORY.layout)
            val lp = prLayoutParams(dialog)

            dialog.apply {
                /* 취소버튼*/
                findViewById<View>(R.id.bt_cancel).setOnClickListener(object : PrSingleClickListener() {
                    override fun onSingleClick(view: View) { dialog.dismiss() }
                })

                /* 계속버튼*/
                findViewById<View>(R.id.bt_continue).setOnClickListener(object : PrSingleClickListener() {
                    override fun onSingleClick(view: View) {
                        func(delHistory)
                        dismiss()
                    }
                })

                show()
                window?.attributes = lp
            }
        }

        /* Dialog - 사용자삭제*/
        fun dialogUserDelete(
            context: Context,
            func:() -> Unit
        ) {

            val dialog = prDialog(context, PrDialogs.DELETE_USER.layout)
            val lp = prLayoutParams(dialog)

            dialog.apply {
                /* 취소버튼*/
                findViewById<View>(R.id.bt_cancel).setOnClickListener(object : PrSingleClickListener() {
                    override fun onSingleClick(view: View) { dialog.dismiss() }
                })

                /* 진행버튼*/
                findViewById<View>(R.id.bt_close).setOnClickListener(object : PrSingleClickListener() {
                    override fun onSingleClick(view: View) {
                        func()
                        dismiss()
                    }
                })

                show()
                window?.attributes = lp
            }
        }

        /* Dialog - 기록없음*/
        fun dialogNoRecordDetail(
            context: Context
        ) {

            val dialog = prDialog(context, PrDialogs.NO_DETAIL.layout)
            val lp = prLayoutParams(dialog)

            dialog.apply {
                findViewById<View>(R.id.bt_close).setOnClickListener(object : PrSingleClickListener() {
                    override fun onSingleClick(view: View) { dialog.dismiss() }
                })

                show()
                window?.attributes = lp
            }
        }

        /* Dialog - 경기없음*/
        fun dialogSaveDailyHistory(
            context: Context
        ) {

            val dialog = prDialog(context, PrDialogs.SAVE_SUCCESS.layout)
            val lp = prLayoutParams(dialog)

            dialog.apply {
                findViewById<View>(R.id.bt_close).setOnClickListener(object : PrSingleClickListener() {
                    override fun onSingleClick(view: View) { dialog.dismiss() }
                })

                show()
                window?.attributes = lp
            }
        }

        /* Dialog - 더블헤더선택*/
        fun dialogSelectDoubleHeader(
                context: Context,
                selectGame:(Int) -> Unit
        ) {

            val dialog = prDialog(context, PrDialogs.TEAM_SELECT_DOUBLE_HEADER.layout)
            val lp = prLayoutParams(dialog)

            dialog.apply {
                /* 선택 : 1경기*/
                findViewById<View>(R.id.bt_first_game).setOnClickListener(object : PrSingleClickListener() {
                    override fun onSingleClick(view: View) {
                        selectGame(0)
                        dismiss()
                    }
                })

                /* 선택 : 2경기*/
                findViewById<View>(R.id.bt_second_game).setOnClickListener(object : PrSingleClickListener() {
                    override fun onSingleClick(view: View) {
                        selectGame(1)
                        dismiss()
                    }
                })

                show()
                window?.attributes = lp
            }
        }
    }
}