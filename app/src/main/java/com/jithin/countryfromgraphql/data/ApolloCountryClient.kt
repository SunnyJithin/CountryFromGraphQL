package com.jithin.countryfromgraphql.data

import com.apollographql.apollo3.ApolloClient
import com.graphql.CountriesQuery
import com.graphql.CountryQuery
import com.jithin.countryfromgraphql.domain.CountryClient
import com.jithin.countryfromgraphql.domain.DetailedCountry
import com.jithin.countryfromgraphql.domain.SimpleCountry

class ApolloCountryClient(
    private val apolloClient: ApolloClient
) : CountryClient {

    override suspend fun getCountries(): List<SimpleCountry> {
        return apolloClient
            .query(CountriesQuery())
            .execute()
            .data
            ?.countries
            ?.map { it.toSimpleCountry() }
            ?: emptyList()
    }

    override suspend fun getCountry(code: String): DetailedCountry? {
        return apolloClient
            .query(CountryQuery(code))
            .execute()
            .data
            ?.country
            ?.toDetailedCountry()
    }
}