grammar Tuple;

tuple
    : object '#' relation '@' user EOF
    | object '#' relation EOF //For wildcard
    | object EOF //For wildcard
    ;

object
    : namespace ':' objectId
    ;

user
    : userId | userset
    ;

userset
    : object '#' relation
    | object EOF //For wildcard
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
