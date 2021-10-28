package com.vass.coursevass.network.service.db

import java.util.*

data class SaveTaskDto (
    val name: String,
    val description:String,
    val status:String ,
    val assignedTo:String,
    val dueDate:Date
)

