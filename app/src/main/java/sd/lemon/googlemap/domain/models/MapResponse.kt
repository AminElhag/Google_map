package sd.lemon.googlemap.domain.models

import com.google.gson.annotations.SerializedName

data class MapResponse(val paths: List<Path>)

data class Path(
    @SerializedName("distance") val distance: Double,
    @SerializedName("bbox") val BBox: List<Double>,
    @SerializedName("points") val points: String
)