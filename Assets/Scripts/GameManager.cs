using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class GameManager : MonoBehaviour
{
    public bool isCubeTurn = false;

    public Cell[] cells;
    // Start is called before the first frame update
    void Start()
    {
        
    }

    public void check()
    {
        //Codi per comprovar si en la primera fila hi ha guanyador
        if (cells[0].status == cells[1].status &&
            cells[1].status == cells[2].status &&
            cells[0].status != 0)
        {
            if (cells[0].status == 1)
            {
                Debug.Log("Spehres han  guanyat");    
            }
            else
            {
                Debug.Log("Cubes han  guanyat");
            }
            
        }
    }

    // Update is called once per frame
    void Update()
    {
        
    }
}
