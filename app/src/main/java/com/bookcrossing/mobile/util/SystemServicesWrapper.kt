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

package com.bookcrossing.mobile.util

import android.content.SharedPreferences

import com.bookcrossing.mobile.location.LocationRepository
import com.bookcrossing.mobile.modules.App
import dagger.Lazy
import javax.inject.Inject

class SystemServicesWrapper {

    @Inject
    lateinit var preferencesLazy: Lazy<SharedPreferences>
    @Inject
    lateinit var appLazy: Lazy<App>
    @Inject
    lateinit var locationRepositoryLazy: Lazy<LocationRepository>

    val preferences: SharedPreferences
        get() = preferencesLazy.get()

    val app: App
        get() = appLazy.get()

    val locationRepository: LocationRepository
        get() = locationRepositoryLazy.get()
}
