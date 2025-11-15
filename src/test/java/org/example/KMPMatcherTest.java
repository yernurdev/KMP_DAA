package org.example;

import org.example.algorithm.KMPMatcher;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class KMPMatcherTest {

    @Test
    public void testSimpleMatch() {
        KMPMatcher kmp = new KMPMatcher("abc");
        assertEquals(2, kmp.search("xxabcxxabc").size());
    }

    @Test
    public void testNoMatch() {
        KMPMatcher kmp = new KMPMatcher("xyz");
        assertTrue(kmp.search("aaaaa").isEmpty());
    }

    @Test
    public void testOverlap() {
        KMPMatcher kmp = new KMPMatcher("aaa");
        assertEquals(3, kmp.search("aaaaaa").size());
    }
}
