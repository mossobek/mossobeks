package com.stirkaparus.driver.presentation

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
import com.stirkaparus.driver.graphs.HomeNavGraph
import com.stirkaparus.driver.presentation.components.ProgressDialog
import kotlinx.coroutines.launch

const val TAG = "HomeScreen"

@SuppressLint(
    "UnusedMaterialScaffoldPaddingParameter",
    "CoroutineCreationDuringComposition",
    "UnrememberedMutableState"
)
@Composable
fun HomeScreen(
    navigateToProfileVerifyScreen: () -> Unit,
    mainViewModel: MainViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController(),

    ) {
    val isLoading = mutableStateOf(false)
    val navigateToProfile = mutableStateOf(false)
    val context = LocalContext.current

    Scaffold(bottomBar = { BottomBar(navController = navController) }) { paddingValue ->
        HomeNavGraph(navController = navController, paddingValue)

        LaunchedEffect(Unit) {
            if (navigateToProfile.value) {
                navigateToProfileVerifyScreen()
            }
        }


        if (mainViewModel.getUserDataFromDataStore(context)) {
            //getUserDataFromDataStore() false
            isLoading.value = false
            Log.e(TAG, "HomeScreen: 1")

        } else {
            mainViewModel.tryGetUserDataFromFireStore(success = {
                isLoading.value = false
                if (!mainViewModel.getUserDataFromDataStore(context)) {
                    navigateToProfile.value = true
                    Log.e(TAG, "HomeScreen: 2")
                }
            }, failure = {
                isLoading.value = false
                //navigate to verify profile
                navigateToProfile.value = true
                Log.e(TAG, "HomeScreen: 3")
            })
        }

        if (isLoading.value) {
            ProgressDialog()
        }
    }
}


@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        BottomBarScreen.Orders,
        BottomBarScreen.Add,
        BottomBarScreen.Reports,
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
    screen: BottomBarScreen, currentDestination: NavDestination?, navController: NavHostController
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