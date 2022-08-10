package com.sh.membership.common.id

interface IdGenerator {
    fun nextId() : Long
}