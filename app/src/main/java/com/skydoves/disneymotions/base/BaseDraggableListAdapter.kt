package com.skydoves.disneymotions.base

import android.view.MotionEvent
import android.view.View
import com.skydoves.baserecyclerviewadapter.BaseAdapter
import com.skydoves.baserecyclerviewadapter.BaseViewHolder
import com.skydoves.baserecyclerviewadapter.SectionRow
import com.skydoves.disneymotions.callback.ItemMoveCallbackListener
import com.skydoves.disneymotions.model.Poster
import com.skydoves.disneymotions.view.viewholder.PosterLineViewHolder
import java.util.*

/**
 * Created by hasanbayhan on 27.04.2020
 **/

open class BaseDraggableListAdapter(
        open var listener: ItemDragListener
) : BaseAdapter(), ItemMoveCallbackListener.Listener {


    open var posters: List<Poster> = listOf()

    override fun layout(sectionRow: SectionRow) = 0

    override fun viewHolder(layout: Int, view: View): BaseViewHolder {
        return PosterLineViewHolder(view) // By default value
    }

    override fun onBindViewHolder(viewHolder: BaseViewHolder, position: Int) {
        val data = objectFromPosition(position)

        try {
            viewHolder.bindData(data)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        viewHolder.itemView.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN && viewHolder.onLongClick(v)) {
                this.listener.onDragStart(viewHolder)
            }
            return@setOnTouchListener false
        }
    }

    override fun onRowMoved(fromPosition: Int, toPosition: Int) {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(posters, i, i + 1)
            }
        } else {
            for (i in toPosition until fromPosition) {
                if (i == 0) {
                    break
                }
                Collections.swap(posters, i, i - 1)
            }
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onRowSelected(itemViewHolder: BaseViewHolder) {

    }

    override fun onRowClear(itemViewHolder: BaseViewHolder) {

    }
}

interface ItemDragListener {
    fun onDragStart(viewHolder: BaseViewHolder)
}