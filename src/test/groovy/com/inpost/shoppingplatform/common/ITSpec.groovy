package com.inpost.shoppingplatform.common

import com.inpost.shoppingplatform.ShoppingPlatformApplication
import org.spockframework.spring.EnableSharedInjection
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK

/** Base spec for integration tests that use DB only. Supports a shared Postgres container for all classes
 * allowing for faster startup. Uses only MOCK web environment, allowing for @Transactional support.
 */
@EnableSharedInjection
@SpringBootTest(classes = ShoppingPlatformApplication.class, webEnvironment = MOCK)
@ActiveProfiles("integration-test")
class ITSpec extends Specification {}