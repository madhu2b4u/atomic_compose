package com.demo.sport.main.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import com.demo.sport.main.data.models.Sport
import com.demo.sport.main.presentation.viewmodel.SportViewModel


@Composable
fun FeaturedSportView(viewModel: SportViewModel) {


    val dataState: LiveData<Sport> = viewModel.data // Your LiveData here
    val showLoaderState: LiveData<Boolean> = viewModel.showLoader // Your LiveData here

    // Observe LiveData using observeAsState()
    val observedDataState: Sport? by dataState.observeAsState(null)
    val observedShowLoaderState: Boolean? by showLoaderState.observeAsState(false)
    val resultState by viewModel.result.observeAsState()

    // Show the main content Column if the loader state is false, else show the loader
    if (observedShowLoaderState == false) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(end = 16.dp)
            ) {
                observedDataState?.let { sport ->
                    Text(
                        text = sport.name,
                        style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(16.dp)
                    )
                    Text(
                        text = sport.description,
                        style = TextStyle(fontSize = 14.sp),
                        modifier = Modifier.padding(16.dp)
                    )
                }

                Button(
                    onClick = { viewModel.getSports() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ) {
                    Text(text = "Refresh", color = Color.White)
                }
            }
        }
    } else {
        // Show the loader when showLoaderState is true
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                color = Color.Red,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}
