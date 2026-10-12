package cstjean.mobile2.tp.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.res.stringResource
import cstjean.mobile2.tp.R
import cstjean.mobile2.tp.data.TypeActivite
import cstjean.mobile2.tp.viewmodel.CreationEntrainementUIState
import cstjean.mobile2.tp.viewmodel.CreationEntrainementsViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreationEntrainementScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onEntrainementCreated: () -> Unit,
    viewModel: CreationEntrainementsViewModel = viewModel()
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.new_training)) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->

        CreationEntrainementContent(
            uiState = uiState,
            formulaireValide = uiState.formulaireValide,
            onTitreChange = viewModel::modifierTitre,
            onTypeActiviteChange = viewModel::modifierTypeActivite,
            onLieuChange = viewModel::modifierLieu,
            onEstExterieurChange = viewModel::modifierEstExterieur,
            onIntensiteChange = viewModel::modifierIntensite,
            onNotesConsignesChange = viewModel::modifierNotesConsignes,
            modifier = Modifier.padding(innerPadding),
            onSaveClick = {
                viewModel.creerEntrainement(onEntrainementCreated)
            }
        )
    }
}

@Composable
fun CreationEntrainementContent(
    formulaireValide: Boolean = false,
    uiState: CreationEntrainementUIState,
    onTitreChange: (String) -> Unit,
    onTypeActiviteChange: (TypeActivite) -> Unit,
    onLieuChange: (String) -> Unit,
    onEstExterieurChange: (Boolean) -> Unit,
    onIntensiteChange: (Float) -> Unit,
    onNotesConsignesChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    onSaveClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = uiState.titre,
            onValueChange = onTitreChange,
            label = { Text(stringResource(R.string.training_title)) },
            modifier = Modifier.fillMaxWidth()
        )
        uiState.erreurTitre?.let {
            Text(
                text = stringResource(it),
                modifier = Modifier.padding(bottom = 8.dp),
                color = MaterialTheme.colorScheme.error
            )
        }

        DropDownActivite(
            selectionActuelle = uiState.typeActivite,
            activites = uiState.listeTypeActivite,
            onSelectionChange = onTypeActiviteChange,
            modifier = Modifier.fillMaxWidth()
        )

        uiState.erreurTypeActivite?.let {
            Text(
                text = stringResource(it),
                modifier = Modifier.padding(bottom = 8.dp),
                color = MaterialTheme.colorScheme.error
            )
        }

        OutlinedTextField(
            value = uiState.lieu,
            onValueChange = onLieuChange,
            label = { Text(stringResource(R.string.place)) },
            modifier = Modifier.fillMaxWidth()
        )

        uiState.erreurLieu?.let {
            Text(
                text = stringResource(it),
                modifier = Modifier.padding(bottom = 8.dp),
                color = MaterialTheme.colorScheme.error
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(stringResource(R.string.outdoor_training))
            Switch(
                checked = uiState.estExterieur,
                onCheckedChange = onEstExterieurChange
            )
        }

        Column {
            Text(
                text = stringResource(R.string.intensity_value, uiState.intensite.toInt()),
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Slider(
                value = uiState.intensite,
                onValueChange = onIntensiteChange,
                valueRange = 1f..10f,
                steps = 8
            )
        }

        OutlinedTextField(
            value = uiState.notesConsignes,
            onValueChange = onNotesConsignesChange,
            label = { Text(stringResource(R.string.notes_instructions)) },
            minLines = 3,
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = onSaveClick,
            modifier = Modifier.fillMaxWidth(),
            enabled = formulaireValide
        ) {
            Text(stringResource(R.string.save))
        }
    }
}

@Composable
fun DropDownActivite(
    selectionActuelle: TypeActivite?,
    activites: List<TypeActivite>,
    onSelectionChange: (TypeActivite) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val texteAffiche = if (selectionActuelle == null) {
        stringResource(R.string.select_activity)
    } else {
        stringResource(selectionActuelle.labelRes)
    }

    Box(modifier = modifier) {
        OutlinedTextField(
            value = texteAffiche,
            onValueChange = {},
            readOnly = true,
            label = { Text(stringResource(R.string.activity_type)) },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = stringResource(R.string.expand_list)
                )
            },
            modifier = Modifier.fillMaxWidth()
        )

        Box(
            modifier = Modifier
                .matchParentSize()
                .clickable { expanded = true }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            activites.forEach { label ->
                DropdownMenuItem(
                    text = { Text(stringResource(label.labelRes)) },
                    onClick = {
                        onSelectionChange(label)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
@Preview
fun CreationEntrainementPreview(){
    CreationEntrainementScreen(
        onBackClick = {},
        onEntrainementCreated = {}
    )
}