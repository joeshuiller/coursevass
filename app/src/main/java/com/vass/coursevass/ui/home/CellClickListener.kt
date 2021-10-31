package com.vass.coursevass.ui.home

import com.vass.coursevass.network.service.db.TaskListDto

interface CellClickListener {
    fun onCellClickListener(data: TaskListDto)
}