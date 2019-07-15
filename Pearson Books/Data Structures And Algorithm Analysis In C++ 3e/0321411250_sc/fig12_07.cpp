    void insert( const Comparable & x )
    {
        static BinaryNode *newNode = NULL;
    
        if( newNode == NULL )
            newNode = new BinaryNode;
        newNode->element = x;
    
        if( root == nullNode )
        {
            newNode->left = newNode->right = nullNode;
            root = newNode;
        }
        else
        {
            splay( x, root );
            if( x < root->element )
            {
                newNode->left = root->left;
                newNode->right = root;
                root->left = nullNode;
                root = newNode;
            }
            else
            if( root->element < x )
            {
                newNode->right = root->right;
                newNode->left = root;
                root->right = nullNode;
                root = newNode;
            }
            else
                return;
        }
        newNode = NULL;   // So next insert will call new
    }
