using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Cell : MonoBehaviour
{
    public GameManager gameManger;
    public GameObject sphere;
    public GameObject cube;
    // Start is called before the first frame update
    void Start()
    {
        sphere.SetActive(false);
        cube.SetActive(false);
    }

    // Update is called once per frame
    void Update()
    {
        
    }
    
    void OnMouseDown()
    {
        if (gameManger.isCubeTurn == true) {
            cube.SetActive(true);
            sphere.SetActive(false);
            gameManger.isCubeTurn = false;
        }
        else
        {
            sphere.SetActive(true);    
            cube.SetActive(false);
            gameManger.isCubeTurn = true;
        }
    }
}
