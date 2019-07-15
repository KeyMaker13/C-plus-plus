    struct Node
    {
        Object  data;
        Node   *prev;
        Node   *next;

        Node( const Object & d = Object( ), Node *p = NULL, Node *n = NULL )
          : data( d ), prev( p ), next( n ) { }
    };
