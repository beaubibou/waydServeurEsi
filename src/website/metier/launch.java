package website.metier;

import java.util.ArrayList;

public class launch
{

    public static void main(String[] args)
    {
        // DOCUMENTEZ_MOI Raccord de méthode auto-généré
   Pagination pagination= new Pagination(10000, 31,100,15,2);
   //    public Pagination(int nbrLigneTotal, int pageAafficher, int nbrLigneAfficheParPage,
// int nbrMaxDePagination, int indexPaginationAfficher)
//
   
   
ArrayList<String> indexpage=pagination.getPagination();
System.out.println(pagination.toString());
for (String numPage:indexpage){
    System.out.println(numPage);
}
System.out.println("next"+pagination.next);
System.out.println("before"+pagination.before);  
    }

    
}
