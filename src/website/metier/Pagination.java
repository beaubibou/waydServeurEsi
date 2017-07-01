package website.metier;

import java.util.ArrayList;

public class Pagination
{

    int nbrLigneTotal, pageAafficher, nbrLigneAfficheParPage;

    int debut;

    int fin;

    int nbrPage;

    int nbrMaxDePagination;

    int indexPaginationAfficher;

    boolean next = false, before = false;
    
   

    // nbrLigneTotal correspond au nombre de ligne de la liste à afficher
    // correspond à la page à afficher par défaut 0 au lancement.
    // nbrMaxDePagination. correspond au nombre de numero de page à afficer dans la pagincation 1-2-3-X

    public Pagination(int nbrLigneTotal, int pageAafficher, int nbrLigneAfficheParPage,
        int nbrMaxDePagination, int indexPaginationAfficher)
    {
        super();
        this.nbrLigneTotal = nbrLigneTotal;
        this.pageAafficher = pageAafficher;
        this.nbrLigneAfficheParPage = nbrLigneAfficheParPage;
        this.nbrMaxDePagination = nbrMaxDePagination;
        this.indexPaginationAfficher = indexPaginationAfficher;
        caculBorne();

    }

    
    
    private void caculBorne()
    {

        // Calcule les indicde du tableau à renvoyer sur la liste

        if (nbrLigneAfficheParPage >= nbrLigneTotal)
        {
            nbrPage = 1;
            debut = 0;
            fin = nbrLigneTotal;
            return;
        }

        if (nbrLigneTotal % nbrLigneAfficheParPage == 0)
        {
            nbrPage = nbrLigneTotal / nbrLigneAfficheParPage;
        }
        else

            nbrPage = nbrLigneTotal / nbrLigneAfficheParPage + 1;

        if (nbrPage < pageAafficher)
        {
            debut = 0;
            fin = 0;
            System.out.println("Page n'exite pas numero trop eleve");
            return;

        }

        if (pageAafficher == nbrPage)
        {// Derniere page

            debut = (nbrPage - 1) * nbrLigneAfficheParPage;
            fin = nbrLigneTotal - 1;
            return;
        }

        if (pageAafficher != nbrPage)
        {

            debut = (pageAafficher - 1) * nbrLigneAfficheParPage;
            fin = (pageAafficher) * nbrLigneAfficheParPage - 1;
            return;
        }

        // *****************************************************

        // ******************************Calcul la pagination ******************
        // Permet de gérer la list de page à afficher en bas.

        // DOCUMENTEZ_MOI Raccord de méthode auto-généré

    }

    @Override
    public String toString()
    {
        return "Pagination [nbrLigneTotal=" + nbrLigneTotal + ", page=" + pageAafficher + ", nbrLigneAffiche=" + nbrLigneAfficheParPage
            + ", debut="
            + debut + ", fin=" + fin + ", nbrPage=" + nbrPage + "]";
    }

    public int getNbrLigneTotal()
    {
        return nbrLigneTotal;
    }

    public void setNbrLigneTotal(int nbrLigneTotal)
    {
        this.nbrLigneTotal = nbrLigneTotal;
    }

    public int getPage()
    {
        return pageAafficher;
    }

    public void setPage(int page)
    {
        this.pageAafficher = page;
    }

    public int getNbrLigneAffiche()
    {
        return nbrLigneAfficheParPage;
    }

    public void setNbrLigneAffiche(int nbrLigneAffiche)
    {
        this.nbrLigneAfficheParPage = nbrLigneAffiche;
    }

    public ArrayList<String> getPagination()
    {
        ArrayList<String> retour = new ArrayList<String>();

        if (nbrPage <= nbrMaxDePagination)
        {

            for (int f = 1; f <= nbrPage; f++)
            {
                retour.add("" + f);
            }
            next = false;
            before = false;

            return retour;
        }

        if (nbrMaxDePagination < nbrPage)
        {
            if (indexPaginationAfficher > 1)
                before = true;
            int max;
            if (indexPaginationAfficher * (nbrMaxDePagination) + 1 > nbrPage)
            {
                max = nbrPage;
                next = false;
            }
            else
            {
                max = indexPaginationAfficher * (nbrMaxDePagination) + 1;
                next = true;
            }

            for (int f = (indexPaginationAfficher * nbrMaxDePagination) - nbrMaxDePagination + 1; f <= max; f++)
            {
                retour.add("" + f);
            }

            return retour;
        }
        return null;
    }



	public int getDebut() {
		return debut;
	}



	public void setDebut(int debut) {
		this.debut = debut;
	}



	public int getFin() {
		return fin;
	}



	public void setFin(int fin) {
		this.fin = fin;
	}

}
