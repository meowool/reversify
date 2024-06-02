package ai.sider

import com.meowool.reflectify.common.android.enableEdgeToEdge
import com.meowool.reflectify.common.android.isSystemDarkMode
import com.meowool.reflectify.event.EventCenter
import com.meowool.reflectify.model.AppDataPreferences
import com.meowool.reflectify.navigation.Route
import com.meowool.reflectify.network.NetworkEvent
import ai.sider.feature.home.homeScreen
import ai.sider.feature.login.loginScreen
import ai.sider.feature.onboarding.onboardingScreen
import ai.sider.ui.SiderTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  @Inject lateinit var appData: AppDataPreferences

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val isSystemDarkMode = isSystemDarkMode()
    val windowInsetsController = enableEdgeToEdge(
      statusBarDarkIcons = !isSystemDarkMode,
      navigationBarDarkIcons = !isSystemDarkMode,
    )
    setContent {
      SiderTheme(
        activity = this,
        insetsController = windowInsetsController,
        useDarkMode = isSystemDarkMode,
        content = { NavHost() },
      )
    }

    lifecycleScope.launch {
      repeatOnLifecycle(Lifecycle.State.STARTED) {
        collectEvents()
      }
    }
  }

  @Composable
  private fun NavHost() {
    val navController = rememberNavController()
    val startDestination = when (appData.get().loginInfo) {
      null -> Route.Login
      else -> Route.Home
    }
    NavHost(navController, startDestination) {
      loginScreen(navController)
      homeScreen(navController)
      onboardingScreen(navController)
    }
  }

  private suspend fun collectEvents() {
    // Handle network-related errors
    EventCenter.observe<NetworkEvent.ResultFailure> { (exception) ->
      // when (exception) {
      //   is ApiException -> TODO()
      //   is NetworkException.BadConnection -> TODO()
      //   is NetworkException.FatalError -> TODO()
      //   is NetworkException.Forbidden -> TODO()
      //   is NetworkException.InternalServer -> TODO()
      //   is NetworkException.NotFound -> TODO()
      //   is NetworkException.TooManyRequests -> TODO()
      //   is NetworkException.UnknownError -> TODO()
      // }
    }
    // Handle other events ...
  }
}
