package com.example.homecreditbank.di

import com.example.homecreditbank.application.di.applicationModules
import com.example.homecreditbank.auth.di.authModules
import com.example.homecreditbank.shop.di.mainModules


val modules = mainModules + authModules + applicationModules