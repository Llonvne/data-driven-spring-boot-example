package cn.llonvne.example.req2resp

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class UnauthorizedResponseEntity<T : Any>(t: T? = null) : ResponseEntity<T>(t, HttpStatus.UNAUTHORIZED)