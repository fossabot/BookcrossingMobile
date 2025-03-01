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

package com.bookcrossing.mobile.presenters

import com.bookcrossing.mobile.ui.profile.AcquiredBookItemView

import moxy.InjectViewState

/**
 * Presenter for handling state of book acquired by user
 */
@InjectViewState
class AcquiredBookItemPresenter : BasePresenter<AcquiredBookItemView>() {

  /**
   * Update book info to mark it as released
   */
  fun releaseCurrentBook(key: String, position: String) {
    acquiredBooks().child(key).removeValue()
    books().child(key).child("city").setValue(getCity())
    books().child(key).child("positionName").setValue(position)
    books().child(key).child("free").setValue(true)
  }

  companion object {
    const val TAG = "AcquiredBookItemPresenter"
  }
}
