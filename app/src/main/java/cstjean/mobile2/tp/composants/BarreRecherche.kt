package cstjean.mobile2.tp.composants

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.AndroidUiModes
import androidx.compose.ui.tooling.preview.Preview
import cstjean.mobile2.tp.R

@Composable
fun BarreRecherche(
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit = {}
)
{
    var searchText by remember { mutableStateOf("") }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,

    ) {
        OutlinedTextField(
            value = searchText,
            onValueChange = { newValue ->
                searchText =
                    newValue
                onSearch(newValue)
            },
            label = { Text(stringResource(R.string.search)) },
            modifier = Modifier.fillMaxWidth()
        )

    }
}

@Preview(showBackground = false)
@Preview(showBackground = false,
    uiMode = AndroidUiModes.UI_MODE_NIGHT_YES)
@Composable
fun BarreRecherchePreview() {
    BarreRecherche()
}