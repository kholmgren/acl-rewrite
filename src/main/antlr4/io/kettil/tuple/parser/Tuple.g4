grammar Tuple;

tuple
    : object '#' relation '@' user EOF
    ;

//objectRelation
//    : object '#' relation
//    ;

object
    : namespace ':' objectId
    ;

user
    : userId | userset
    ;

userset
    : object '#' relation
    ;

namespace
    : IDENTIFIER
    ;

objectId
    : IDENTIFIER
    ;

relation
    : IDENTIFIER
    ;

userId
    : IDENTIFIER
    ;

IDENTIFIER
   : ~[#@:]*
   ;

//WS
//   : [ \t\n\r\u00A0\uFEFF\u2003] + -> skip
//   ;
