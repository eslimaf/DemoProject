package com.eslimaf.coroutines.data.marvel.api.model.entity

/*
id (int, optional): The unique ID of the series resource.,
title (string, optional): The canonical title of the series.,
description (string, optional): A description of the series.,
resourceURI (string, optional): The canonical URL identifier for this resource.,
urls (Array[Url], optional): A set of public web site URLs for the resource.,
startYear (int, optional): The first year of publication for the series.,
endYear (int, optional): The last year of publication for the series (conventionally, 2099 for ongoing series) .,
rating (string, optional): The age-appropriateness rating for the series.,
modified (Date, optional): The date the resource was most recently modified.,
thumbnail (Image, optional): The representative image for this series.,
comics (ComicList, optional): A resource list containing comics in this series.,
stories (StoryList, optional): A resource list containing stories which occur in comics in this series.,
events (EventList, optional): A resource list containing events which take place in comics in this series.,
characters (CharacterList, optional): A resource list containing characters which appear in comics in this series.,
creators (CreatorList, optional): A resource list of creators whose work appears in comics in this series.,
next (SeriesSummary, optional): A summary representation of the series which follows this series.,
previous (SeriesSummary, optional): A summary representation of the series which preceded this series. */
data class Series(
    val id: Int?,
    val title: String?,
    val description: String?,
    val thumbnail: Image?
)