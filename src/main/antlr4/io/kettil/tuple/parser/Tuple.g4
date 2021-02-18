grammar Tuple;

tuple
    : object '#' relation '@' user EOF
    ;

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
    : name=IDENTIFIER
    ;

objectId
    : id=IDENTIFIER
    ;

relation
    : name=IDENTIFIER
    ;

userId
    : id=IDENTIFIER
    ;

IDENTIFIER
   : ~[#@:]*
   ;

//WS
//   : [ \t\n\r\u00A0\uFEFF\u2003] + -> skip
//   ;
