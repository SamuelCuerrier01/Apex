package cstjean.mobile2.tp.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cstjean.mobile2.tp.viewmodel.AccueilViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import cstjean.mobile2.tp.data.Entrainement
import cstjean.mobile2.tp.R

@Composable
fun AccueilScreen(

    viewModel: AccueilViewModel = viewModel(),
    onCreerSeanceClick: () -> Unit = {},
    onLastEntrainementClick: (Entrainement) -> Unit = {},
    onTousEntrainementsClick: () -> Unit = {}
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
                        text = stringResource(R.string.home),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }
    ) { paddingValues ->
        if(!uiState.isLoading){
            AccueilComposant(
                paddingValues = paddingValues,
                entrainementsCompletes = uiState.entrainementsCompletes,
                entrainements = uiState.entrainements,
                progressRatio = uiState.entrainementsCompletes.size.toFloat() / uiState.entrainements.size.toFloat(),
                dernierEntrainement = uiState.entrainements.lastOrNull(),
                onCreerSeanceClick = { onCreerSeanceClick() },
                onLastEntrainementClick = { onLastEntrainementClick(it) },
                onTousEntrainementsClick = { onTousEntrainementsClick() }
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
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

@Composable
fun AccueilComposant(
    paddingValues: androidx.compose.foundation.layout.PaddingValues,
    entrainementsCompletes: List<Entrainement> = emptyList(),
    entrainements: List<Entrainement> = emptyList(),
    progressRatio: Float = 0f,
    dernierEntrainement: Entrainement? = null,
    onCreerSeanceClick: () -> Unit = {},
    onLastEntrainementClick: (Entrainement) -> Unit = {},
    onTousEntrainementsClick: () -> Unit = {}
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(
                        color = MaterialTheme.colorScheme.secondary,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(horizontal = 20.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.trainings),
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "${entrainementsCompletes.size} / ${entrainements.size}",
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
            }
        }

        Text(
            modifier = Modifier.fillMaxWidth().padding(top = 20.dp),
            text = stringResource(R.string.progress, (progressRatio * 100).toInt()),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        LinearProgressIndicator(
            progress = progressRatio,
            modifier = Modifier.fillMaxWidth().padding(bottom = 30.dp)

        )

        Text(
            text = stringResource(R.string.next_training),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(
                    color = MaterialTheme.colorScheme.secondary,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(horizontal = 20.dp)
                .clickable(onClick = { onLastEntrainementClick(dernierEntrainement!!) }),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                if (!entrainements.isEmpty()){
                    val dernierEntrainement = dernierEntrainement
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = dernierEntrainement?.titre ?: stringResource(R.string.training_title), color = MaterialTheme.colorScheme.onPrimary)
                        Text(text = dernierEntrainement?.typeActivite?.let { stringResource(it.labelRes) } ?: "", color = MaterialTheme.colorScheme.onPrimary)
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = dernierEntrainement?.lieu ?: stringResource(R.string.place), color = MaterialTheme.colorScheme.onPrimary)
                        Text(text = dernierEntrainement?.intensite.toString(), color = MaterialTheme.colorScheme.onPrimary)
                    }
                } else {
                    Text(
                        text = stringResource(R.string.no_upcoming_training),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

            }
        }



        Button(
            onClick = { onTousEntrainementsClick() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.all_trainings))
        }

        Button(
            onClick = { onCreerSeanceClick() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.create))
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AccueilPreview() {
    AccueilScreen(

    )
}