const checkMetamask = function() {
    if (typeof window.ethereum !== 'undefined') {
        console.log('MetaMask is installed!')
        return true
    } else {
        alert('Please install the metamask first')
        return false
    }
}

const connectMetamask = async function() {
    if (!checkMetamask()) return false
    let accounts = null
    try {
        accounts = await ethereum.request({ method: 'eth_requestAccounts' })
    } catch (e) {
        console.log(e)
        return false
    }
    return accounts[0]
}

export { checkMetamask, connectMetamask }