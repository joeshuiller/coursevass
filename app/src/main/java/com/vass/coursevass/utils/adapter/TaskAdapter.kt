package com.vass.coursevass.utils.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.vass.coursevass.R
import com.vass.coursevass.network.service.db.TaskListDto
import com.vass.coursevass.ui.home.CellClickListener


class TaskAdapter(private val context: Context, private val dataSet: List<TaskListDto>,
                  private val cellClickListener: CellClickListener
) :
    RecyclerView.Adapter<TaskAdapter.ViewHolder>(){

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val infoText: TextView = view.findViewById(R.id.item_title)
        val description: TextView = view.findViewById(R.id.item_descrip)

        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(context)
            .inflate(R.layout.card_task, viewGroup, false)
        val data = dataSet[viewType]
        view.setOnClickListener(View.OnClickListener { v ->
            val bundle = bundleOf("id" to data.id, "name" to data.name, "description" to data.description, "assignedTo" to data.assignedTo,"status" to data.status)
            Log.d("verificacion", "esto $bundle")
            v.findNavController().navigate(R.id.nav_detailshow, bundle)
        })
        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        val data = dataSet[position]
        viewHolder.infoText.text = data.name
        viewHolder.description.text = data.description
        viewHolder.itemView.setOnClickListener {
            cellClickListener.onCellClickListener(data)
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return dataSet.size
    }
}