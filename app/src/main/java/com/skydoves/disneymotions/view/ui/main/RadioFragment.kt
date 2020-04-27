/*
 * Designed and developed by 2020 skydoves (Jaewoong Eum)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.skydoves.disneymotions.view.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import com.skydoves.baserecyclerviewadapter.BaseViewHolder
import com.skydoves.disneymotions.R
import com.skydoves.disneymotions.base.DatabindingFragment
import com.skydoves.disneymotions.base.ItemDragListener
import com.skydoves.disneymotions.callback.ItemMoveCallbackListener
import com.skydoves.disneymotions.databinding.FragmentRadioBinding
import com.skydoves.disneymotions.view.adapter.PosterCircleAdapter
import org.koin.android.viewmodel.ext.android.getViewModel

class RadioFragment : DatabindingFragment(), ItemDragListener {

  private lateinit var touchHelper: ItemTouchHelper

  override fun onCreateView(
          inflater: LayoutInflater,
          container: ViewGroup?,
          savedInstanceState: Bundle?
  ): View? {

    val posterCircleAdapter = PosterCircleAdapter(this)

    val binding = binding<FragmentRadioBinding>(inflater, R.layout.fragment_radio, container).apply {
      viewModel = getViewModel<MainViewModel>().apply { fetchDisneyPosterList() }
      lifecycleOwner = this@RadioFragment
      adapter = posterCircleAdapter
    }
    val callBack = ItemMoveCallbackListener(posterCircleAdapter)
    touchHelper = ItemTouchHelper(callBack)
    touchHelper.attachToRecyclerView(binding.recyclerView)
    return binding.root
  }

  override fun onDragStart(viewHolder: BaseViewHolder) {
    touchHelper.startDrag(viewHolder)
  }
}
