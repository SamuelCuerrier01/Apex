package cstjean.mobile2.tp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import cstjean.mobile2.tp.data.Entrainement
import cstjean.mobile2.tp.data.FiltreRecherche
import cstjean.mobile2.tp.data.TypeActivite
import cstjean.mobile2.tp.composants.BarreRecherche
import cstjean.mobile2.tp.composants.EntrainementCard
import cstjean.mobile2.tp.viewmodel.ListeRechercheViewModel
import cstjean.mobile2.tp.R

@Composable
fun ListeRechercheScreen(

    viewModel: ListeRechercheViewModel = viewModel(),
    onEntrainementClick: (Entrainement) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()




    Scaffold(
        topBar = {
            Surface(
                color = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier.fillMaxWidth()
                    .statusBarsPadding()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                        .padding(16.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = stringResource(R.string.search),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }
    ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    BarreRecherche(
                        onSearch = {viewModel.chargerEntrainementRecherche(it)}
                    )
                    FilterChipsRow(
                        activites = uiState.listeTypeActivite,
                        listeFiltres = uiState.filtresActives,
                        onFiltreClick = { viewModel.
                        chargerEntrainementFiltre(it) }
                    )
                    if (!uiState.isLoading){
                    ListeRechercheComposant(
                        entrainements = uiState.entrainements,
                        toggleFavoris = { viewModel.toggleFavoris(it) },
                        onEntrainementClick = { onEntrainementClick(it) }
                    )} else {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding),
                            contentAlignment = Alignment.Center
                        ) {
                            LinearProgressIndicator(
                                modifier = Modifier.width(100.dp),
                                color = MaterialTheme.colorScheme.secondary,
                                trackColor = MaterialTheme.colorScheme.surfaceVariant,
                            )
                        }
                }

            }
    }
}

@Composable
fun ListeRechercheComposant(
    entrainements: List<Entrainement> = emptyList(),
    toggleFavoris: (Entrainement) -> Unit = {},
    onEntrainementClick: (Entrainement) -> Unit = {}
){

    if (!entrainements.isEmpty()){
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(entrainements) { entrainement ->
                EntrainementCard(
                    entrainement = entrainement,
                    onEntrainementClick = { onEntrainementClick(entrainement) },
                    onFavoriteClick = { toggleFavoris(entrainement) },
                )
            }
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                textAlign = TextAlign.Center,
                modifier = Modifier.width(200.dp),
                color = MaterialTheme.colorScheme.secondary,
                text = stringResource(R.string.no_training_found)
            )
        }

    }

}


@Composable
fun FilterChipsRow(
    activites: List<TypeActivite>,
    listeFiltres: List<Enum<*>> = emptyList(),
    onFiltreClick: (Enum<*>) -> Unit = {}


){

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            FilterChip(
                selected = listeFiltres.contains(FiltreRecherche.FAVORIS),
                onClick = { onFiltreClick(FiltreRecherche.FAVORIS) },
                label = {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = stringResource(R.string.star),
                        tint = Color(0XFFBD8328),
                    )
                    Text(stringResource(R.string.favorites))
                }
            )
        }

        item {
            FilterChip(
                selected = listeFiltres.contains(FiltreRecherche.TOUS),
                onClick = { onFiltreClick(FiltreRecherche.TOUS) },
                label = { Text(stringResource(R.string.all)) }
            )
        }

        items(activites) { activite ->
            FilterChip(
                selected = listeFiltres.contains(activite),
                onClick = { onFiltreClick(activite) },
                label = { Text(stringResource(activite.labelRes)) }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ListeRecherchePreview() {
    ListeRechercheScreen(

    )
}