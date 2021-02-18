//package io.kettil.tuple;
//
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Data
//@NoArgsConstructor
//public class TupleUser {
//    private static final String EXCLUSIVE_ERR_MSG = "Either userId or userSet must be non-null";
//
//    private String userId;
//    private TupleUserset userSet;
//
//    public TupleUser(String userId, TupleUserset userSet) {
//        if (userId == null && userSet == null)
//            throw new NullPointerException(EXCLUSIVE_ERR_MSG);
//        if (userId != null && userSet != null)
//            throw new IllegalArgumentException(EXCLUSIVE_ERR_MSG);
//
//        this.userId = userId;
//        this.userSet = userSet;
//    }
//
//    @Override
//    public String toString() {
//        return userSet != null
//            ? userSet.toString()
//            : userId;
//    }
//}
