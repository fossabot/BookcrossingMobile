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

package com.bookcrossing.mobile.util.adapters

import android.view.View
import butterknife.OnClick
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.WhichButton.POSITIVE
import com.afollestad.materialdialogs.actions.getActionButton
import com.afollestad.materialdialogs.input.getInputField
import com.afollestad.materialdialogs.input.input
import com.bookcrossing.mobile.R
import com.bookcrossing.mobile.models.Book
import com.bookcrossing.mobile.ui.bookpreview.BookItemView

class AcquiredBooksViewHolder(view: View) : BooksViewHolder(view), BookItemView {

  override fun bind(book: Book) {
    bookName!!.text = book.name
    author!!.text = book.author
  }

  @OnClick(R.id.release_button) fun release() {
    MaterialDialog(itemView.context).show {
      title(R.string.release_book_dialog_title)
      positiveButton(R.string.release_book, click = { dialog ->
        itemPresenter.releaseCurrentBook(
            key,
            dialog.getInputField().text.toString()
        )
      })
      input(
          hintRes = R.string.hint_position, prefillRes = R.string.filler_position,
          callback = { dialog, input: CharSequence ->
            dialog.getActionButton(POSITIVE)
                .isEnabled = !(input.length < 5 || input.length > 50)
          })
    }
  }
}
