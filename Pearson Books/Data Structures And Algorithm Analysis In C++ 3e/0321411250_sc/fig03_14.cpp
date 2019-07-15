    class const_iterator
    {
      public:
        const_iterator( ) : current( NULL )
          { }
    
        const Object & operator* ( ) const
          { return retrieve( ); }
            
        const_iterator & operator++ ( )
        {
            current = current->next;
            return *this;
        }
    
        const_iterator operator++ ( int )
        {
            const_iterator old = *this;
            ++( *this );
            return old;
        }
    
        bool operator== ( const const_iterator & rhs ) const
          { return current == rhs.current; }
        bool operator!= ( const const_iterator & rhs ) const
          { return !( *this == rhs ); }
    
      protected:
        Node *current;
    
        Object & retrieve( ) const
          { return current->data; }
    
        const_iterator( Node *p ) : current( p )
          { }
            
        friend class List<Object>;
    };
