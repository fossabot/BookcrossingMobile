/*
 *    Copyright 2019 Andrey Mukamolov
 *
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

package com.bookcrossing.mobile.components

import com.bookcrossing.mobile.modules.ApiModule
import com.bookcrossing.mobile.modules.App
import com.bookcrossing.mobile.modules.LocationModule
import com.bookcrossing.mobile.modules.PrefModule
import com.bookcrossing.mobile.modules.ProvidersModule
import com.bookcrossing.mobile.presenters.MainPresenter
import com.bookcrossing.mobile.presenters.SearchPresenter
import com.bookcrossing.mobile.util.FirebaseWrapper
import com.bookcrossing.mobile.util.SystemServicesWrapper
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
 * (c) 2016 Andrey Mukamolov aka fobo66 <fobo66@protonmail.com>
 * Created by fobo66 on 15.11.2016.
 */
@Singleton
@Component(modules = [PrefModule::class, LocationModule::class, ApiModule::class, ProvidersModule::class])
interface AppComponent {
  @Component.Builder
  interface Builder {
    @BindsInstance
    fun application(application: App): Builder

    fun prefModule(prefModule: PrefModule): Builder

    fun apiModule(apiModule: ApiModule): Builder

    fun locationModule(locationModule: LocationModule): Builder

    fun build(): AppComponent
  }

  fun inject(app: App)

  fun inject(firebaseWrapper: FirebaseWrapper)

  fun inject(systemServicesWrapper: SystemServicesWrapper)

  fun inject(presenter: MainPresenter)

  fun inject(presenter: SearchPresenter)
}
