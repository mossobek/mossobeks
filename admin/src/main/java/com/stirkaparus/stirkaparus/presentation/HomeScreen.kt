package com.stirkaparus.stirkaparus.presentation

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.stirkaparus.stirkaparus.BottomBarScreen
import com.stirkaparus.stirkaparus.MainViewModel
import com.stirkaparus.stirkaparus.graphs.HomeNavGraph
import com.stirkaparus.stirkaparus.presentation.components.VControl
import kotlinx.coroutines.delay

const val TAG = "HomeScreen"

@SuppressLint(
    "UnusedMaterialScaffoldPaddingParameter",
    "CoroutineCreationDuringComposition",
    "UnrememberedMutableState"
)
@Composable
fun HomeScreen(
    navToUpdateScreen: () -> Unit,
    navigateToUserActivationScreen: () -> Unit,
    mainViewModel: MainViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController()
) {

    val isLoading = mutableStateOf(true)
    val navigateToProfile = mutableStateOf<Boolean?>(null)
    val context = LocalContext.current
    VControl(navToUpdateScreen = navToUpdateScreen)
    Scaffold(
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) { paddingValue ->
        HomeNavGraph(navController = navController, paddingValue)

        when (navigateToProfile.value) {
            true -> {
                LaunchedEffect(Unit) {
                    delay(1000L)
                    isLoading.value = false
                    navigateToUserActivationScreen()
                }
            }
            false -> {
                isLoading.value = false

            }
            else -> {

            }
        }



        if (mainViewModel.getUserDataFromDataStore()) {
            //getUserDataFromDataStore() false
            navigateToProfile.value = false

            Log.e(TAG, "HomeScreen: 1")

        } else {
            mainViewModel.tryGetUserDataFromFireStore(
                success = {
                    if (!mainViewModel.getUserDataFromDataStore()) {
                        navigateToProfile.value = true

                        Log.e(TAG, "HomeScreen: 2")
                    } else {
                        navigateToProfile.value = false
                    }
                }, failure = {
                    //navigate to verify profile
                    navigateToProfile.value = true

                    Log.e(TAG, "HomeScreen: 3")
                })
        }

        if (isLoading.value) {
            ProgressDialog()
        }
        Log.e(TAG, "HomeScreen: ${navigateToProfile.value}")
    }
}



@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.Orders,
        BottomBarScreen.Add,
        BottomBarScreen.Reports,
        BottomBarScreen.Profile,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomBarDestination = screens.any { it.route == currentDestination?.route }
    if (bottomBarDestination) {
        BottomNavigation(
            modifier = Modifier.height(50.dp), backgroundColor = (Color.White)
        ) {

            screens.forEach { screen ->
                AddItem(
                    screen = screen,
                    currentDestination = currentDestination,
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    BottomNavigationItem(

        icon = {
            Icon(
                imageVector = screen.icon, contentDescription = "Navigation Icon"
            )
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
        onClick = {
            navController.navigate(screen.route) {

                popUpTo(navController.graph.findStartDestination().id)

                launchSingleTop = true

            }
        })
}