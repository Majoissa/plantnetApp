using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Cell : MonoBehaviour
{
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
}
