package com.apx5.apx5.ui.dialogs

import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.apx5.apx5.R
import com.apx5.apx5.datum.adapter.AdtTeamSelection
import com.apx5.apx5.datum.pitcher.PtDelHistory

/**
 * DialogActivity
 */

class DialogActivity : AppCompatActivity() {

    companion object {
        /* Dialog 생성*/
        fun prDialog(
            context: Context,
            layout: Int): Dialog {

            val dialog = Dialog(context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(layout)
            dialog.setCancelable(false)

            return dialog
        }

        /* Layout Params 생성*/
        fun prLayoutParams(
            dialog: Dialog): WindowManager.LayoutParams {

            val lp = WindowManager.LayoutParams()
            lp.copyFrom(dialog.window?.attributes)
            lp.width = WindowManager.LayoutParams.WRAP_CONTENT
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT

            return lp
        }

        /* Dialog - 서버사용불가*/
        fun dialogNoInternet(
            context: Context,
            func:() -> Unit) {

            val dialog = prDialog(context, R.layout.dialog_no_internet)
            val lp = prLayoutParams(dialog)

            dialog.findViewById<View>(R.id.bt_close).setOnClickListener {
                func()
            }

            dialog.show()
            dialog.window?.attributes = lp
        }

        /* Dialog - 일반에러*/
        fun dialogError(
            context: Context) {

            val dialog = prDialog(context, R.layout.dialog_comm_error)
            val lp = prLayoutParams(dialog)

            dialog.findViewById<View>(R.id.bt_close).setOnClickListener {
                dialog.dismiss()
            }

            dialog.show()
            dialog.window?.attributes = lp
        }

        /* Dialog - 팀선택*/
        fun dialogTeamSelect(
            context: Context,
            team: AdtTeamSelection,
            func:(String) -> Unit) {

            val dialog = prDialog(context, R.layout.dialog_team_select)
            val lp = prLayoutParams(dialog)

            /* 팀이미지*/
            val ivIcon = dialog.findViewById<ImageView>(R.id.iv_icon)
            ivIcon.setImageDrawable(ContextCompat.getDrawable(context, team.teamImage))

            /* 팀컬러*/
            val lytTeamInfo = dialog.findViewById<LinearLayout>(R.id.lyt_team_info)
            val lytAcion = dialog.findViewById<LinearLayout>(R.id.lyt_action)
            lytTeamInfo.setBackgroundColor(team.teamColor)
            lytAcion.setBackgroundColor(team.teamColor)

            /* 팀코드*/
            val teamCode = team.teamCode

            /* 취소버튼*/
            dialog.findViewById<View>(R.id.bt_cancel).setOnClickListener { dialog.dismiss() }

            /* 진행버튼*/
            dialog.findViewById<View>(R.id.bt_close).setOnClickListener {
                func(teamCode)
                dialog.dismiss()
            }

            dialog.show()
            dialog.window?.attributes = lp
        }

        /* Dialog - 기록삭제*/
        fun dialogHistoryDelete(
            context: Context,
            delHistory: PtDelHistory,
            func:(PtDelHistory) -> Unit) {

            val dialog = prDialog(context, R.layout.dlg_history_delete)
            val lp = prLayoutParams(dialog)

            /* 취소버튼*/
            dialog.findViewById<View>(R.id.bt_cancel).setOnClickListener { dialog.dismiss() }

            /* 계속버튼*/
            dialog.findViewById<View>(R.id.bt_continue).setOnClickListener {
                func(delHistory)
                dialog.dismiss()
            }

            dialog.show()
            dialog.window?.attributes = lp
        }

        /* Dialog - 사용자삭제*/
        fun dialogUserDelete(
            context: Context,
            func:() -> Unit) {

            val dialog = prDialog(context, R.layout.dialog_del_user)
            val lp = prLayoutParams(dialog)

            /* 취소버튼*/
            dialog.findViewById<View>(R.id.bt_cancel).setOnClickListener { dialog.dismiss() }

            /* 진행버튼*/
            dialog.findViewById<View>(R.id.bt_close).setOnClickListener {
                func()
                dialog.dismiss()
            }

            dialog.show()
            dialog.window?.attributes = lp
        }

        /* Dialog - 기록없음*/
        fun dialogNoRecordDetail(
            context: Context) {

            val dialog = prDialog(context, R.layout.dlg_record_no_detail)
            val lp = prLayoutParams(dialog)

            dialog.findViewById<View>(R.id.bt_follow).setOnClickListener { dialog.dismiss() }

            dialog.show()
            dialog.window?.attributes = lp
        }

        /* Dialog - 경기없음*/
        fun dialogSaveDailyHistory(
            context: Context) {

            val dialog = prDialog(context, R.layout.dialog_save_success)
            val lp = prLayoutParams(dialog)

            dialog.findViewById<View>(R.id.bt_close).setOnClickListener { dialog.dismiss() }

            dialog.show()
            dialog.window?.attributes = lp
        }

        /* Dialog - 더블헤더선택*/
        fun dialogSelectDoubleHeader(
                context: Context,
                selectGame:(Int) -> Unit) {

            val dialog = prDialog(context, R.layout.dlg_select_double_header)
            val lp = prLayoutParams(dialog)

            /* 선택 : 1경기*/
            dialog.findViewById<View>(R.id.bt_first_game).setOnClickListener {
                selectGame(0)
                dialog.dismiss()
            }

            /* 선택 : 2경기*/
            dialog.findViewById<View>(R.id.bt_second_game).setOnClickListener {
                selectGame(1)
                dialog.dismiss()
            }

            dialog.show()
            dialog.window?.attributes = lp
        }
    }
}