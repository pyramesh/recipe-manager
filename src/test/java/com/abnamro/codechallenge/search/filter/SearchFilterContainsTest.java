package com.abnamro.codechallenge.search.filter;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.abnamro.codechallenge.search.SearchOperation;

public class SearchFilterContainsTest {

    @Test
    public void couldBeAppliedReturnsFalseWhenOperationIsNotEqual() {
        SearchFilterContains filter  = new SearchFilterContains();
        boolean b = filter.couldBeApplied(SearchOperation.NOT_EQUAL);
        assertFalse(b);
    }

    @Test
    public void couldBeAppliedReturnsFalseWhenOperationIsEqual() {
        SearchFilterContains filter  = new SearchFilterContains();
        boolean b = filter.couldBeApplied(SearchOperation.EQUAL);
        assertFalse(b);

    }

    @Test
    public void couldBeAppliedReturnsFalseWhenOperationIsDoesNotContain() {
        SearchFilterContains filter  = new SearchFilterContains();
        boolean b = filter.couldBeApplied(SearchOperation.DOES_NOT_CONTAIN);
        assertFalse(b);
    }

    @Test
    public void couldBeAppliedReturnsTrueWhenOperationIsContain() {
        SearchFilterContains filter  = new SearchFilterContains();
        boolean b = filter.couldBeApplied(SearchOperation.CONTAINS);
        assertTrue(b);
    }

    @Test
    public void couldBeAppliedReturnsFalseWhenOperationIsNull() {
        SearchFilterContains filter  = new SearchFilterContains();
        boolean b = filter.couldBeApplied(null);
        assertFalse(b);
    }

}