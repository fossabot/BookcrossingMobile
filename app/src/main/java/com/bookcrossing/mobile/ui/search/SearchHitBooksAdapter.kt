/*
 *    Copyright  2019 Andrey Mukamolov
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.bookcrossing.mobile.ui.search


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import butterknife.BindView
import com.bookcrossing.mobile.R
import com.bookcrossing.mobile.models.SearchHitBook
import com.bookcrossing.mobile.modules.GlideApp
import com.bookcrossing.mobile.ui.search.SearchHitBooksAdapter.ViewHolder
import com.bookcrossing.mobile.util.adapters.BaseViewHolder
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.google.firebase.storage.FirebaseStorage

class SearchHitBooksAdapter : PagedListAdapter<SearchHitBook, ViewHolder>(SearchHitBooksAdapter) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view = LayoutInflater.from(parent.context)
      .inflate(R.layout.hits_item, parent, false)
    return ViewHolder(view)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val item = getItem(position)
    if (item != null) {
      holder.bind(item)
    }
  }

  inner class ViewHolder(itemView: View) : BaseViewHolder(itemView) {

    @BindView(R.id.search_hit_cover)
    lateinit var cover: ImageView

    @BindView(R.id.search_hit_book_name)
    lateinit var bookName: TextView

    @BindView(R.id.search_hit_author)
    lateinit var author: TextView

    @BindView(R.id.search_hit_current_position)
    lateinit var currentPosition: TextView

    fun bind(book: SearchHitBook) {
      GlideApp.with(itemView.context)
        .load(FirebaseStorage.getInstance().getReference(book.objectID + ".jpg"))
        .placeholder(R.drawable.ic_book_cover_placeholder).transition(withCrossFade())
        .into(cover)

      bookName.text = book.name
      author.text = book.author
      currentPosition.text = book.positionName
    }
  }

  companion object : DiffUtil.ItemCallback<SearchHitBook>() {
    override fun areItemsTheSame(oldItem: SearchHitBook, newItem: SearchHitBook): Boolean {
      return oldItem.objectID == newItem.objectID
    }

    override fun areContentsTheSame(oldItem: SearchHitBook, newItem: SearchHitBook): Boolean {
      return oldItem == newItem
    }

  }
}
