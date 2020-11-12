package com.apx5.apx5.ui.recordteam

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.joda.time.DateTime

class BottomRecordAdapterTest() {

}
//class BottomRecordAdapterTest(val workType: MpWorkType? = null) : RecyclerView.Adapter<WorkListAdapter.WorkListViewHolder>() {
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkListViewHolder {
//        val binding = DataBindingUtil.inflate<ItemWorkListBinding>(
//            LayoutInflater.from(parent.context),
//            R.layout.item_work_list,
//            parent,
//            false
//        )
//        return WorkListViewHolder(binding)
//    }
//
//    var list: List<MpWorkRecordEntity> = listOf()
//
//    fun updateList(list: List<MpWorkRecordEntity>) {
//        this.list = list
//    }
//
//    override fun getItemCount(): Int = list.size
//
//    override fun onBindViewHolder(holder: WorkListViewHolder, position: Int) {
//        val item = list[position]
//        holder.bind(item)
//    }
//
//    inner class WorkListViewHolder(val binding: ItemWorkListBinding) : RecyclerView.ViewHolder(binding.root) {
//        fun bind(item: MpWorkRecordEntity) {
//
//            if (list.size == 1) {
//                binding.vwUnderline.visibilityExt(false)
//            } else {
//                binding.vwUnderline.visibilityExt(true)
//            }
//
//            val cal = MpDate(item.date)
////            binding.tvDate.text = dayOfWeekToString(cal.dateTime.dayOfWeek)
//            binding.tvDate.text = convertDateTimeToString(item.date, dayOfWeekPattern)
//            binding.tvDateNum.text = convertDateTimeToString(item.date, dayPattern)
//
//            if (MpEmployeeWorkStatus.START_UNCHECK.status in item.status) {
//                listOf(
//                    binding.tvInWork,
//                    binding.tvInWorkNoon,
//                    binding.tvOutWorkNoon,
//                    binding.tvOutWork,
//                    binding.tvDash
//                ).visibilityExt(false)
//                binding.tvTotalTime.text = convertMillisToTime(0)
//            } else {
//                listOf(
//                    binding.tvInWork,
//                    binding.tvInWorkNoon,
//                    binding.tvOutWorkNoon,
//                    binding.tvOutWork,
//                    binding.tvDash
//                ).visibilityExt(true)
//
//                val inWork = changeNoon(convertDateTimeToString(item.checkedStart, timePattern))
//                binding.tvInWorkNoon.text = binding.root.context.getString(inWork.first)
//                binding.tvInWork.text = inWork.second
//
//                val isToday = DateTime(item.checkedStart).withTimeAtStartOfDay().millis == DateTime.now().withTimeAtStartOfDay().millis
//                if (item.checkedEnd == item.checkedStart && isToday)  {
//                    binding.tvOutWorkNoon.text = ""
//                    binding.tvOutWork.text = ""
//                    binding.tvDash.visibilityExt(false)
//
//                    workType?.let {
//                        binding.tvTotalTime.text = convertMillisToTime(TimeUtils.calcTotalTime(item, it))
//                    }
//
//                } else {
//                    binding.tvDash.visibilityExt(true)
//
//                    val outWork = changeNoon(convertDateTimeToString(item.checkedEnd, timePattern))
//                    binding.tvOutWorkNoon.text = binding.root.context.getString(outWork.first)
//                    binding.tvOutWork.text = outWork.second
//
//                    if (item.checkedEnd == item.checkedStart) {
//                        if (MpEmployeeWorkStatus.ABSENCE.status in item.status) {
//                            /* 결근 */
//                            listOf(binding.tvInWork, binding.tvInWorkNoon, binding.tvOutWork, binding.tvOutWorkNoon).visibilityExt(false)
//                        } else if (MpEmployeeWorkStatus.VACATION.status in item.status ||
//                            MpEmployeeWorkStatus.YEAR_ANNUAL.status in item.status || MpEmployeeWorkStatus.AM_ANNUAL.status in item.status ||
//                            MpEmployeeWorkStatus.PM_ANNUAL.status in item.status) {
//                            /* 휴가일 경우 출근기록이 없으면 */
//                            if (item.checkedStart == 0L) {
//                                binding.tvInWork.text = ""
//                                binding.tvInWorkNoon.text = ""
//                                binding.tvOutWorkNoon.text = ""
//                                binding.tvOutWork.text = ""
//                                listOf(binding.tvInWork, binding.tvInWorkNoon, binding.tvOutWork, binding.tvOutWorkNoon).visibilityExt(false)
//                            } else {
//                                /* 퇴근 시간이 없는 경우 */
//                                binding.tvOutWorkNoon.text = ""
//                                binding.tvOutWork.text = ""
//                            }
//                        } else {
//                            /* 퇴근 시간이 없는 경우 */
//                            binding.tvOutWorkNoon.text = ""
//                            binding.tvOutWork.text = ""
//                        }
////                        binding.tvTotalTime.text = convertMillisToTime(0)
//                        binding.tvDash.visibilityExt(false)
//                    }
//
//                    workType?.let {
//                        binding.tvTotalTime.text = convertMillisToTime(TimeUtils.calcTotalTime(item, it))
//                    }
//                }
//            }
//
//            setStatus(item, binding)
//        }
//    }
//
//    /* 근무자 상태는 이론상 최대 3개가 max */
//    fun setStatus(item: MpWorkRecordEntity, binding: ItemWorkListBinding) {
//        binding.rvStatus.layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
//        binding.rvStatus.adapter = WorkListStatusAdapter(item.status)
//    }
//
//    companion object {
//        const val TAG = "WorkListAdapter"
//    }
//}