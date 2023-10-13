package com.mcwindy.ddrhelper.network

import android.util.Log
import com.mcwindy.ddrhelper.model.DdnetRankData
import com.mcwindy.ddrhelper.model.TablerowRankData
import com.squareup.moshi.Moshi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.net.HttpURLConnection
import java.net.URL

const val DDNET_BASE_URL = "https://ddnet.org"

/** Build the Moshi object with Kotlin adapter factory that Retrofit will be using. */
private val moshi = Moshi.Builder().build()

/** The Retrofit object with the Moshi converter. */
private val retrofit = Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(DDNET_BASE_URL).build()

/** A public Api object that exposes the lazy-initialized Retrofit service */
object DdnetApi {
    private const val TAG = "DdnetApi"
    val retrofitService: DdnetApiService by lazy {
        retrofit.create(DdnetApiService::class.java)
    }

    suspend fun getRankData(scope: CoroutineScope): DdnetRankData? {
        var result: DdnetRankData? = null

        val url = URL("$DDNET_BASE_URL/ranks/")
        val urlConnection = withContext(Dispatchers.IO) {
            url.openConnection()
        } as HttpURLConnection

        scope.launch(Dispatchers.IO) {
            try {
                val ret = urlConnection.inputStream.bufferedReader().readText()
                result = parseRankData(ret)
            } catch (e: Exception) {
                Log.e(TAG, "Failure: ${e.message}")
            } finally {
                urlConnection.disconnect()
            }
        }.join()

        return result
    }
}

fun parseRankData(data: String): DdnetRankData {
    val doc = Jsoup.parse(data)

    val pointsList: Elements = doc.select("#global > div:nth-child(9) > table > tbody > tr")
    val teamRankPointsList: Elements =
        doc.select("#global > div:nth-child(10) > table > tbody > tr")
    val rankPointsList: Elements = doc.select("#global > div:nth-child(11) > table > tbody > tr")
    val points365List: Elements = doc.select("#global > div:nth-child(13) > table > tbody > tr")
    val points30List: Elements = doc.select("#global > div:nth-child(14) > table > tbody > tr")
    val points7List: Elements = doc.select("#global > div:nth-child(15) > table > tbody > tr")


    val ret = DdnetRankData()

    val tmpList = ArrayList<TablerowRankData>()
    for (row in pointsList) {
        val indexElement = row.selectFirst("td.rankglobal")
        val pointsElement = row.selectFirst("td.points")
        val flagElement = row.selectFirst("td.flag > img")
        val nameElement = row.selectFirst("td > a")

        val index = indexElement?.text()?.replace(".", "")?.toIntOrNull() ?: continue
        val name = nameElement?.text() ?: continue
        val points = pointsElement?.text()?.replace(" pts", "")?.toInt() ?: continue
        val country = flagElement?.attr("alt")?.lowercase() ?: continue

        val rowData = TablerowRankData(index, name, points, country)
        tmpList.add(rowData)
    }
    ret.point = tmpList.toList()
    tmpList.clear()
    for (row in teamRankPointsList) {
        val indexElement = row.selectFirst("td.rankglobal")
        val pointsElement = row.selectFirst("td.points")
        val flagElement = row.selectFirst("td.flag > img")
        val nameElement = row.selectFirst("td > a")

        val index = indexElement?.text()?.replace(".", "")?.toIntOrNull() ?: continue
        val name = nameElement?.text() ?: continue
        val points = pointsElement?.text()?.replace(" pts", "")?.toInt() ?: continue
        val country = flagElement?.attr("alt")?.lowercase() ?: continue

        val rowData = TablerowRankData(index, name, points, country)
        tmpList.add(rowData)
    }
    ret.teamRank = tmpList.toList()
    tmpList.clear()
    for (row in rankPointsList) {
        val indexElement = row.selectFirst("td.rankglobal")
        val pointsElement = row.selectFirst("td.points")
        val flagElement = row.selectFirst("td.flag > img")
        val nameElement = row.selectFirst("td > a")

        val index = indexElement?.text()?.replace(".", "")?.toIntOrNull() ?: continue
        val name = nameElement?.text() ?: continue
        val points = pointsElement?.text()?.replace(" pts", "")?.toInt() ?: continue
        val country = flagElement?.attr("alt")?.lowercase() ?: continue

        val rowData = TablerowRankData(index, name, points, country)
        tmpList.add(rowData)
    }
    ret.rank = tmpList.toList()
    tmpList.clear()
    for (row in points365List) {
        val indexElement = row.selectFirst("td.rankglobal")
        val pointsElement = row.selectFirst("td.points")
        val flagElement = row.selectFirst("td.flag > img")
        val nameElement = row.selectFirst("td > a")

        val index = indexElement?.text()?.replace(".", "")?.toIntOrNull() ?: continue
        val name = nameElement?.text() ?: continue
        val points = pointsElement?.text()?.replace(" pts", "")?.toInt() ?: continue
        val country = flagElement?.attr("alt")?.lowercase() ?: continue

        val rowData = TablerowRankData(index, name, points, country)
        tmpList.add(rowData)
    }
    ret.point365 = tmpList.toList()
    tmpList.clear()
    for (row in points30List) {
        val indexElement = row.selectFirst("td.rankglobal")
        val pointsElement = row.selectFirst("td.points")
        val flagElement = row.selectFirst("td.flag > img")
        val nameElement = row.selectFirst("td > a")

        val index = indexElement?.text()?.replace(".", "")?.toIntOrNull() ?: continue
        val name = nameElement?.text() ?: continue
        val points = pointsElement?.text()?.replace(" pts", "")?.toInt() ?: continue
        val country = flagElement?.attr("alt")?.lowercase() ?: continue

        val rowData = TablerowRankData(index, name, points, country)
        tmpList.add(rowData)
    }
    ret.point30 = tmpList.toList()
    tmpList.clear()
    for (row in points7List) {
        val indexElement = row.selectFirst("td.rankglobal")
        val pointsElement = row.selectFirst("td.points")
        val flagElement = row.selectFirst("td.flag > img")
        val nameElement = row.selectFirst("td > a")

        val index = indexElement?.text()?.replace(".", "")?.toIntOrNull() ?: continue
        val name = nameElement?.text() ?: continue
        val points = pointsElement?.text()?.replace(" pts", "")?.toInt() ?: continue
        val country = flagElement?.attr("alt")?.lowercase() ?: continue

        val rowData = TablerowRankData(index, name, points, country)
        tmpList.add(rowData)
    }
    ret.point7 = tmpList.toList()

    return ret
}