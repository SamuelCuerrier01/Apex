package cstjean.mobile2.tp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import cstjean.mobile2.tp.screens.AccueilScreen
import cstjean.mobile2.tp.screens.CreationEntrainementScreen
import cstjean.mobile2.tp.screens.EntrainementScreen
import cstjean.mobile2.tp.ui.navigation.AccueilRoute
import cstjean.mobile2.tp.ui.navigation.CreationEntrainementRoute
import cstjean.mobile2.tp.ui.navigation.EntrainementRoute
import cstjean.mobile2.tp.composants.BottomNavBar

import cstjean.mobile2.tp.screens.ListeRechercheScreen
import cstjean.mobile2.tp.ui.navigation.ListeRechercheRoute

@Composable
fun EntrainementApp() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavBar(
                modifier = Modifier,
                onAccueilClick = {
                    navController.navigate(AccueilRoute)
                },
                onListeRechercheClick = {
                    navController.navigate(ListeRechercheRoute)
                },
                onCreateClick = {
                    navController.navigate(CreationEntrainementRoute)
                }
            )
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = innerPadding.calculateBottomPadding())
        ) {

            EntrainementNavHost(
                navController = navController,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
private fun EntrainementNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = AccueilRoute,
        modifier = modifier
    ) {
        /*composable<AboutRoute> {
            AboutScreen()
        }*/

        composable<AccueilRoute> {
            AccueilScreen(

                onCreerSeanceClick = {
                    navController.navigate(CreationEntrainementRoute)
                },
                onLastEntrainementClick = { entrainement ->
                    navController.navigate(EntrainementRoute(idEntrainement = entrainement.id))
                },
                onTousEntrainementsClick = {
                    navController.navigate(ListeRechercheRoute)
                }
            )
        }

        composable<CreationEntrainementRoute> {
            CreationEntrainementScreen(
                onBackClick = { navController.popBackStack() },
                onEntrainementCreated = {
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("refresh_needed", true)
                    navController.popBackStack()
                }
            )
        }

        composable<EntrainementRoute> { backStackEntry ->
            val route: EntrainementRoute = backStackEntry.toRoute()
            EntrainementScreen(
                idEntrainement = route.idEntrainement,
                onDelete = {
                    navController.popBackStack()
                }
            )
        }

        composable<ListeRechercheRoute> {
            ListeRechercheScreen(
                onEntrainementClick = { entrainement ->
                    navController.navigate(EntrainementRoute(idEntrainement = entrainement.id))
                }
            )
        }

        /*composable<DetailsRoute> { backStackEntry ->
            val route: DetailsRoute = backStackEntry.toRoute()

            MediaDetailsScreen(
                idFilm = route.idFilm,
                onBackClick = { navController.popBackStack() }
            )
        }*/
    }
}

