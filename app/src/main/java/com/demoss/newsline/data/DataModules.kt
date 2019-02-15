package com.demoss.newsline.data

import com.demoss.newsline.data.local.localDataModules
import com.demoss.newsline.data.remote.remoteDataModules
import com.demoss.newsline.data.repository.repositoryDataModule

val dataModules = localDataModules +
        remoteDataModules +
        repositoryDataModule