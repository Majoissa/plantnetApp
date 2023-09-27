using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using TMPro;
public class GameManager : MonoBehaviour
{
    public bool isCubeTurn = false;
    public TextMeshProUGUI label;
    public Cell[] cells;
    // Start is called before the first frame update
    void Start()
    {
        label.text = "hola";
    }

    // Suponiendo que las cells se disponen de la siguiente forma:
    // 0 | 1 | 2
    // 3 | 4 | 5
    // 6 | 7 | 8

    public void CheckWinner()
    {
        bool isDraw = true;

        // Revisa las filas
        for (int i = 0; i < 9; i += 3)
        {
            if (cells[i].status != 0 && cells[i].status == cells[i + 1].status && cells[i + 1].status == cells[i + 2].status)
            {
                DeclareWinner(cells[i].status);
                return;
            }
            if (cells[i].status == 0 || cells[i + 1].status == 0 || cells[i + 2].status == 0) isDraw = false;
        }

        // Revisa las columnas
        for (int i = 0; i < 3; i++)
        {
            if (cells[i].status != 0 && cells[i].status == cells[i + 3].status && cells[i + 3].status == cells[i + 6].status)
            {
                DeclareWinner(cells[i].status);
                return;
            }
        }

        // Revisa las diagonales
        if (cells[0].status != 0 && cells[0].status == cells[4].status && cells[4].status == cells[8].status)
        {
            DeclareWinner(cells[0].status);
            return;
        }

        if (cells[2].status != 0 && cells[2].status == cells[4].status && cells[4].status == cells[6].status)
        {
            DeclareWinner(cells[2].status);
            return;
        }

        // Si todas las celdas están llenas y no hay ganador, entonces es un empate.
        if (isDraw)
        {
            Debug.Log("¡Es un empate!");
        }
    }

    void DeclareWinner(int status)
    {
        if (status == 1)
            Debug.Log("Spheres han ganado");
        else
            Debug.Log("Cubes han ganado");
    }

    // Update is called once per frame
    void Update()
    {
    }
}
