package io.kettil.rewrite.userset.expression;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum SetOp {
    @JsonProperty("union")
    Union,
    @JsonProperty("intersect")
    Intersect,
    @JsonProperty("exclude")
    Exclude
}
