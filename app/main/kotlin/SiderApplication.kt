package ai.sider

import android.app.Application
import com.tencent.mmkv.MMKV
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SiderApplication : Application() {
  override fun onCreate() {
    super.onCreate()
    MMKV.initialize(this)
  }
}
