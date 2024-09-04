package com.paragon.composechallenges.ui.viewPager

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.paragon.composechallenges.EMPTY
import com.paragon.composechallenges.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CarouselImplementationWithTabIndicators() {
    val pagerState = rememberPagerState(pageCount = { 8 })
    val itemList = listOf(R.mipmap.car0,R.mipmap.car5, R.mipmap.car1, R.mipmap.car2,R.mipmap.car6, R.mipmap.car3, R.mipmap.car4 , R.mipmap.car7)

    val isDragged by pagerState.interactionSource.collectIsDraggedAsState()
    if (isDragged.not()) {
        with(pagerState) {
            var currentPageKey by remember { mutableIntStateOf(0) }
            LaunchedEffect(key1 = currentPageKey) {
                launch {
                    delay(timeMillis = 2000L)
                    val nextPage = (currentPage + 1).mod(pageCount)
                    animateScrollToPage(page = nextPage)
                    currentPageKey = nextPage
                }
            }
        }
    }



    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        HorizontalPager(state = pagerState) { page ->
            CarouselItem(itemList[page])
        }
        Box(
            contentAlignment = Alignment.Center, modifier = Modifier
                .padding(bottom = 100.dp)
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            DotIndicators(
                pageCount = pagerState.pageCount,
                pagerState = pagerState,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }


}

@Composable
fun CarouselItem(id: Int) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
           /* .height(200.dp)*/, Alignment.Center
    ) {
        Card(modifier = Modifier.fillMaxWidth(0.8f)) {
            Image(
                alignment = Alignment.Center,
                painter = painterResource(id = id),
                contentDescription = EMPTY,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DotIndicators(
    modifier: Modifier = Modifier,
    selectedColor: Color = Color.Blue,
    unselectedColor: Color = Color.LightGray,
    pageCount: Int,
    pagerState: PagerState,
) {
    Row(verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        repeat(pageCount) { iteration ->
            val color = if (pagerState.currentPage == iteration) selectedColor else unselectedColor
            Box(
                modifier = modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(color)
            )
        }
    }
}