package com.apx5.apx5.constants

import com.apx5.apx5.R

/**
 * Dialog 종류
 * @return PrDialogs
 */

enum class PrDialogs(
    val layout: Int
) {

    /**
     * 인터넷 연결 확인안됨
     */
    NO_INTERNET(R.layout.dialog_no_internet),

    /**
     * 일반 에러
     */
    COMM_ERROR(R.layout.dialog_comm_error),

    /**
     * 팀 선택
     */
    TEAM_SELECT(R.layout.dialog_team_select),

    /**
     * 팀 선택 (더블헤더경기)
     */
    TEAM_SELECT_DOUBLE_HEADER(R.layout.dialog_select_double_header),

    /**
     * 기록 삭제
     */
    DELETE_HISTORY(R.layout.dialog_history_delete),

    /**
     * 사용자 삭제
     */
    DELETE_USER(R.layout.dialog_del_user),

    /**
     * 상세기록없음
     */
    NO_DETAIL(R.layout.dialog_record_no_detail),

    /**
     * 저장성공
     */
    SAVE_SUCCESS(R.layout.dialog_save_success);
}