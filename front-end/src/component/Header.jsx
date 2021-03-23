import { Menu } from 'antd';
import React, { useState } from 'react';
import { MailOutlined, AppstoreOutlined, SettingOutlined } from '@ant-design/icons';
import { Link } from 'react-router-dom';

const { SubMenu } = Menu;


function Header(props) {
    const [current, setcurrent] = useState("home");
    const handleClick = e => {
        console.log('click ', e);
        setcurrent({ current: e.key });
    };
    return (
        <Menu style={{ marginBottom: 30 }}
            onClick={handleClick} selectedKeys={[current]}
            mode="horizontal">
            <Menu.Item>
                <img
                    src="https://firebasestorage.googleapis.com/v0/b/img-base-d6dac.appspot.com/o/images%2Flogo1.png?alt=media&token=c037d54f-7fc3-402b-8060-afb8ee87ab90"
                    style={{ width: 120, height: 35, margin: 5 }}></img>
            </Menu.Item>
            <Menu.Item key="home">
                <Link to="/">
                    Home
                </Link>
            </Menu.Item>
            {/* <SubMenu key="SubMenu" icon={<SettingOutlined />} title="Navigation Three - Submenu">
                <Menu.ItemGroup title="Item 1">
                    <Menu.Item key="setting:1">Option 1</Menu.Item>
                    <Menu.Item key="setting:2">Option 2</Menu.Item>
                </Menu.ItemGroup>
                <Menu.ItemGroup title="Item 2">
                    <Menu.Item key="setting:3">Option 3</Menu.Item>
                    <Menu.Item key="setting:4">Option 4</Menu.Item>
                </Menu.ItemGroup>
            </SubMenu> */}
        </Menu>
    );
}

export default Header;